import { redirect } from "react-router-dom";
import AxiosClient from "./AxiosClient";
import { removeCookieToken, setRefreshToken } from "../storage/Cookie";

export const signIn = async (email, password) => {
  const response = await AxiosClient({
    method: "post",
    url: "/users/signin",
    data: { email, password },
  })
    .then((resp) => {
      const { accessToken, refreshToken } = resp.data.data;
      AxiosClient.defaults.headers.common["Authorization"] = accessToken;
      localStorage.setItem("accessToken", accessToken);
      setRefreshToken(refreshToken);
      return resp;
    })
    .catch((e) => {
      console.log("error", e);
      alert("LOGIN FAILED");
      redirect("/");
    });
  return response;
};

export const reissue = async (refreshToken) => {
  console.log("reissue: ", refreshToken);
  const oldAceessToken = localStorage.getItem("accessToken");
  const response = await AxiosClient({
    method: "post",
    url: "/users/reissue",
    data: { accessToken: oldAceessToken, refreshToken },
  })
    .then((resp) => {
      console.log("reissue ", resp);
      const { accessToken } = resp.data.data;
      localStorage.setItem("accessToken", accessToken);
      AxiosClient.defaults.headers.common["Authorization"] = accessToken;
      return resp;
    })
    .catch((e) => {
      console.log("error", e);
      alert("reissue failed");
      redirect("/");
    });
  return response;
};

export const reissueErrorHandler = (e) => {
  removeCookieToken();
};