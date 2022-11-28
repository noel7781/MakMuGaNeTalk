import React, { useState, useEffect } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import jwt_decode from "jwt-decode";
import "../css/header.css";
import NotificationsActiveIcon from "@mui/icons-material/NotificationsActive";
import { Link, useNavigate } from "react-router-dom";

const Header = () => {
  const [inviteList, setInviteList] = useState([]);

  const accessToken = localStorage.getItem("accessToken");
  const EventSource = EventSourcePolyfill || NativeEventSource;
  const navigate = useNavigate();

  const handleInviteAlarmClick = (e) => {
    navigate("/invite", { state: { inviteList } });
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
            console.log(data);
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
  console.log(inviteList);
  return (
    <div>
      <h1 className="header">
        <span>MakMuGaNe Talk</span>
        <div onClick={handleInviteAlarmClick} className="notification">
          <NotificationsActiveIcon />
          <span className="badge">{inviteList.length}</span>
        </div>
      </h1>
    </div>
  );
};

export default Header;
