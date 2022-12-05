import React, { useState, useEffect } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";
import { signOut } from "../apis/AuthAPI";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import "../css/header.css";
import { useDispatch, useSelector } from "react-redux";
import { DELETE_TOKEN } from "../Store/Auth";
const Header = () => {
  const [inviteList, setInviteList] = useState([]);

  // const accessToken = localStorage.getItem("accessToken");
  const { accessToken } = useSelector((state) => state.authToken);
  const EventSource = EventSourcePolyfill || NativeEventSource;
  const navigate = useNavigate();
  const dispatch = useDispatch();

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
      console.log("delete Token!");
    }
    return navigate("/");
  };

  useEffect(() => {
    let eventSource;
    if (accessToken) {
      const fetchSse = async () => {
        const decodedToken = jwt_decode(accessToken);
        const userId = decodedToken.userId;
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
            // if (!res.includes("EventStream Created."))  // 알림 목록 업데이트
          };

          /* EVENTSOURCE ONERROR ------------------------------------------------------ */
          eventSource.onerror = async (event) => {
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
          </div>
        ) : (
          <></>
        )}
      </h1>
    </div>
  );
};

export default Header;
