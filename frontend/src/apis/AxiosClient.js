import axios from "axios";
import { getCookieToken } from "../storage/Cookie";
import { reissue, reissueErrorHandler } from "./AuthAPI";

const axiosClient = axios.create({
  baseURL: "http://localhost:8080/api/v1",
  headers: { "Content-Type": "application/json" },
});

axiosClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      config.headers["Authorization"] = token;
    }
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);
axiosClient.interceptors.response.use(
  (response) => {
    return response;
  },
  function (error) {
    const originalRequest = error.config;

    if (error.response.status === 401 && !originalRequest._retry) {
      originalRequest._retry = true;
      const refreshToken = getCookieToken();
      return axiosClient({
        method: "post",
        url: "/users/reissue",
        data: {
          refreshToken,
        },
      }).then((res) => {
        if (res.status === 201) {
          const { accessToken } = res.data.data;
          localStorage.setItem("accessToken", res.data.data.accessToken);
          axios.defaults.headers.common["Authorization"] = accessToken;
          return axios(originalRequest);
        }
      });
    }
    return Promise.reject(error);
  }
);

export default axiosClient;
