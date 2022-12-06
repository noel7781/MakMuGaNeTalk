import { Cookies } from "react-cookie";

const cookies = new Cookies();

const REFRESH_TOKEN_DURATION = 30;

export const setRefreshToken = (refreshToken) => {
  const today = new Date();
  const expireDate = today.setDate(today.getDate() + REFRESH_TOKEN_DURATION);

  return cookies.set("refreshToken", refreshToken, {
    sameSite: "strict",
    path: "/",
    expires: new Date(expireDate),
  });
};

export const getCookieToken = () => {
  return cookies.get("refreshToken");
};

export const removeCookieToken = () => {
  cookies.remove("refreshToken", { sameSite: "strict", path: "/" });
};
