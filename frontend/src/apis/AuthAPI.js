import AxiosClient from "./AxiosClient";
import {
  removeCookieToken,
  setRefreshToken,
  getCookieToken,
} from "../storage/Cookie";

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

export const signUp = async (nickname, email, password) => {
  const response = await AxiosClient({
    method: "post",
    url: "/users/signup",
    data: { password, nickname, email },
  })
    .then((resp) => {
      return resp;
    })
    .catch((e) => {
      console.log("error", e);
    });
};

export const oauthGoogle = async ({ credential }) => {
  const response = await AxiosClient({
    method: "post",
    url: "/users/oauth/google",
    headers: {
      "Content-Type": "application/x-www-form-urlencoded",
    },
    data: { credential },
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

export const reissue = async () => {
  const oldAceessToken = localStorage.getItem("accessToken");
  const refreshToken = getCookieToken();
  const response = await AxiosClient({
    method: "post",
    url: "/users/reissue",
    data: { accessToken: oldAceessToken, refreshToken },
  })
    .then((resp) => {
      const { accessToken, refreshToken } = resp.data;
      localStorage.setItem("accessToken", accessToken);
      AxiosClient.defaults.headers.common["Authorization"] = accessToken;
      setRefreshToken(refreshToken);
      window.location.reload();
      return resp;
    })
    .catch((e) => {
      console.log("error", e);
      alert("reissue failed");
    });
  return response;
};

export const checkNicknameExist = async (nickname) => {
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

export const checkEmailExist = async (email) => {
  const response = await AxiosClient({
    method: "get",
    url: "/users/email-check",
    params: { email: email },
  })
    .then((resp) => {
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

export const signOut = async () => {
  const response = await AxiosClient({
    method: "post",
    url: "/users/signout",
  })
    .then((resp) => {
      localStorage.removeItem("accessToken");
      removeCookieToken();
      return resp;
    })
    .catch((e) => {
      console.error(e);
      return e.response;
    });
  return response;
};

export const changeNickname = async (userId, nickname) => {
  const response = await AxiosClient({
    method: "post",
    url: "/users/nickname",
    data: { userId, nickname },
  })
    .then((resp) => {
      return resp;
    })
    .catch((e) => {
      console.error(e);
      return e.response;
    });
  return response;
};
