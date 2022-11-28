import React, { useEffect } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import "../css/header.css";
import { useState } from "react";

const accessToken = localStorage.getItem("accessToken");
const EventSource = EventSourcePolyfill || NativeEventSource;

console.log("access Token = ", accessToken);
const Header = () => {
  const [inviteList, setInviteList] = useState([]);
  useEffect(() => {
    let eventSource;
    if (accessToken) {
      const fetchSse = async () => {
        try {
          eventSource = new EventSource(
            `http://localhost:8080/api/v1/alarm/subscribe`,
            {
              headers: {
                Authorization: accessToken,
              },
              withCredentials: true,
            }
          );
          console.log(eventSource);

          /* EVENTSOURCE ONMESSAGE ---------------------------------------------------- */
          eventSource.onmessage = async (event) => {
            const res = await event.data;
            console.log("on Message: ", res);
            const data = JSON.parse(res);
            console.log(data);
            if (data.notificationType === "INVITE") {
              console.log("invite");
              const newInviteList = [...inviteList, data];
              console.log("new List = ", newInviteList);
              setInviteList(newInviteList);
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
      return () => eventSource.close();
    }
  });
  console.log("list =", inviteList);
  return (
    <div>
      <h1 className="header">MakMuGaNe Talk</h1>
    </div>
  );
};

export default Header;
