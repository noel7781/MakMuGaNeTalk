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
      const { accessToken, refreshToken } = resp.data;
      AxiosClient.defaults.headers.common["Authorization"] = accessToken;
      localStorage.setItem("accessToken", accessToken);
      setRefreshToken(refreshToken);
      return resp;
    })
    .catch((e) => {
      console.log("error", e);
      alert("LOGIN FAILED");
      return e;
    });
  return response;
};

export const signUp = async (nickname, userId, email, password) => {
  const response = await AxiosClient({
    method: "post",
    url: "/users/signup",
    data: { password, nickname, email },
  })
    .then((resp) => {
      console.log(resp);
    })
    .catch((e) => {
      console.log("error", e);
    });
};

export const reissue = async (refreshToken) => {
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
    });
  return response;
};

export const checkNicknameDuplicate = async (nickname) => {
  const response = await AxiosClient({
    method: "get",
    url: "/users/nickname-check",
    params: { nickname: nickname },
  })
    .then((resp) => {
      return resp;
    })
    .catch((e) => {
      return e.response;
    });
  return response;
};

export const checkEmailDuplicate = async (email) => {
  const response = await AxiosClient({
    method: "get",
    url: "/users/email-check",
    params: { email: email },
  })
    .then((resp) => {
      console.log(resp);
      return resp;
    })
    .catch((e) => {
      return e.response;
    });
  return response;
};

export const reissueErrorHandler = (e) => {
  removeCookieToken();
};
