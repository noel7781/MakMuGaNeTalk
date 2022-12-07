import axiosClient from "../apis/AxiosClient";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import jwt_decode from "jwt-decode";
import { EventSourcePolyfill } from "event-source-polyfill";
import { useNavigate } from "react-router-dom";
import { signOut } from "../apis/AuthAPI";
import { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { DELETE_TOKEN, SET_TOKEN } from "../Store/Auth";
import { setRefreshToken, getCookieToken } from "../storage/Cookie";
import { isExpired } from "../utils/util";
import "../css/header.css";

const Header = () => {
  const { accessToken } = useSelector((state) => state.authToken);
  const [inviteList, setInviteList] = useState([]);

  // const accessToken = localStorage.getItem("accessToken");
  const [userNickname, setUserNickname] = useState("");
  const EventSource = EventSourcePolyfill || NativeEventSource;
  const navigate = useNavigate();
  const dispatch = useDispatch();

  useEffect(() => {
    const token = localStorage.getItem("accessToken");
    if (token) {
      const decoded = jwt_decode(token);
      if (isExpired(decoded)) {
        localStorage.removeItem("accessToken");
      } else {
        dispatch(SET_TOKEN(token));
        setUserNickname(jwt_decode(token).nickname);
      }
    }
  }, []);
  const handleInviteAlarmClick = (e) => {
    navigate("/invite", { state: { inviteList } });
  };
  const handleNavigateMain = () => {
    navigate("/main");
  };
  const handleSignOut = async () => {
    const response = await signOut();
    if (response.status === 200) {
      dispatch(DELETE_TOKEN());
    }
    return navigate("/");
  };

  useEffect(() => {
    let eventSource;
    const token = localStorage.getItem("accessToken");
    dispatch(SET_TOKEN(token));
    if (accessToken) {
      const fetchSse = async () => {
        const decodedToken = jwt_decode(accessToken);
        const userId = decodedToken.userId;
        setUserNickname(decodedToken.nickname);
        try {
          eventSource = new EventSource(
            `http://localhost:8080/api/v1/alarm/subscribe/${userId}`,
            {
              headers: {
                Authorization: accessToken,
              },
              withCredentials: true,
            }
          );

          /* EVENTSOURCE ONMESSAGE ---------------------------------------------------- */
          eventSource.onmessage = async (event) => {
            const res = await event.data;
            console.log("on Message: ", res);
            const data = JSON.parse(res);
            if (data.notificationType === "INVITE") {
              setInviteList((prev) => {
                let newInviteList = [...prev, data];
                return newInviteList;
              });
            }
          };

          /* EVENTSOURCE ONERROR ------------------------------------------------------ */
          eventSource.onerror = async (event) => {
            if (event.status === 401) {
              const refreshToken = getCookieToken();
              const accessToken = localStorage.getItem("accessToken");
              return axiosClient({
                method: "post",
                url: "/users/reissue",
                data: {
                  accessToken,
                  refreshToken,
                },
              })
                .then((res) => {
                  if (res.status === 200) {
                    const { accessToken, refreshToken } = res.data;
                    localStorage.setItem("accessToken", accessToken);
                    // TODO: CHECK 할 부분
                    axiosClient.defaults.headers.common["Authorization"] =
                      accessToken;
                    dispatch(SET_TOKEN(accessToken));
                    setRefreshToken(refreshToken);
                    window.location.reload();
                  }
                })
                .catch((e) => console.log(e));
            }
            if (!event.error.message.includes("No activity"))
              eventSource.close();
          };
        } catch (error) {}
      };
      fetchSse();
      console.log(eventSource);
      return () => eventSource.close();
    }
  }, [accessToken]);
  return (
    <div>
      <h1 className="header">
        <span onClick={handleNavigateMain}>MakMuGaNe Talk</span>
        {accessToken ? (
          <div className="signin">
            <div onClick={handleInviteAlarmClick} className="notification">
              <NotificationsActiveIcon />
              <span className="badge">{inviteList.length}</span>
            </div>
            <button className="signoutbutton" onClick={handleSignOut}>
              로그아웃
            </button>
            <div className="nickname">Nickname : {userNickname}</div>
          </div>
        ) : (
          <></>
        )}
      </h1>
    </div>
  );
};

export default Header;
