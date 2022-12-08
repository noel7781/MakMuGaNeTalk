import axios from "axios";
import { getCookieToken, setRefreshToken } from "../storage/Cookie";

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
  async function (error) {
    const errorCode = error.response.data.status;

    if (errorCode === 401) {
      const refreshToken = getCookieToken();
      const accessToken = localStorage.getItem("accessToken");
      return axiosClient({
        method: "post",
        url: "/users/reissue",
        data: {
          accessToken,
          refreshToken,
        },
      }).then((res) => {
        if (res.status === 200) {
          const { accessToken, refreshToken } = res.data;
          localStorage.setItem("accessToken", accessToken);
          // TODO: CHECK 할 부분
          axiosClient.defaults.headers.common["Authorization"] = accessToken;
          setRefreshToken(refreshToken);
          window.location.reload();
        }
      });
    }
    return Promise.reject(error);
  }
);

export default axiosClient;
