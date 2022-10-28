import React, { useEffect, useState } from "react";
import sockjs from "sockjs-client/dist/sockjs";
import { CompatClient } from "@stomp/stompjs";
import Screen from "./Screen";

const stompClient = new CompatClient();
stompClient.webSocketFactory = function () {
  return new sockjs("http://localhost:8080/ws");
};
const ChatContainer = ({ cid, username }) => {
  const [contents, setContents] = useState([]);
  const [message, setMessage] = useState("");

  useEffect(() => {
    stompClient.connect(
      {},
      () => {
        stompClient.subscribe(`/topic/room.${cid}`, (data) => {
          const newMessage = JSON.parse(data.body);
          addMessage(newMessage);
        });
      },
      (e) => {
        console.error(e);
      }
    );
  }, [contents]);

  const handleEnter = (e) => {
    const newMessage = {
      content: message,
    };
    stompClient.publish({
      destination: `/app/chat.message.${cid}`,
      headers: {},
      body: JSON.stringify(newMessage),
    });
    setMessage("");
    e.preventDefault();
  };
  const addMessage = (message) => {
    setContents((prev) => [
      ...prev,
      {
        content: message.content,
      },
    ]);
  };
  return (
    <div className={"container"}>
      <Screen
        contents={contents}
        handleEnter={handleEnter}
        message={message}
        setMessage={setMessage}
        username={username}
      />
    </div>
  );
};
export default ChatContainer;
