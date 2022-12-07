import sockjs from "sockjs-client/dist/sockjs";
import Screen from "./screen";
import jwt_decode from "jwt-decode";
import { useEffect, useState, useRef } from "react";
import { CompatClient } from "@stomp/stompjs";
import { useParams } from "react-router-dom";
import { timeConvert } from "../utils/util";
import { getMessages } from "../apis/ChatRoomAPI";
import { useDispatch, useSelector } from "react-redux";
import { SET_TOKEN } from "../Store/Auth";
import "../css/chatRoom.css";

const ChatContainer = () => {
  const [contents, setContents] = useState([]);
  const [message, setMessage] = useState("");
  const [userId, setUserId] = useState("");
  const { accessToken } = useSelector((state) => state.authToken);
  const params = useParams();
  const dispatch = useDispatch();
  const stompClient = useRef(new CompatClient());

  let decodedToken;

  useEffect(() => {
    stompClient.current.webSocketFactory = function () {
      return new sockjs("http://localhost:8080/ws");
    };
    stompClient.current.debug = () => {};
    const token = localStorage.getItem("accessToken");
    if (token) {
      dispatch(SET_TOKEN(token));
      decodedToken = jwt_decode(token);
    }
    const fetchData = async () => {
      const response = await getMessages(params.chatRoomId);
      console.log(response);
      if (response.status === 200) {
        const messages = response.data;
        messages.map((msg) => {
          msg.createdAt = timeConvert(msg.createdAt);
        });
        setContents(messages);
      }
    };
    fetchData();
  }, []);
  useEffect(() => {
    if (decodedToken != null) {
      setUserId(decodedToken["sub"]);
    }
  }, [decodedToken]);

  useEffect(() => {
    let token = localStorage.getItem("accessToken");
    stompClient.current.connect(
      { Authorization: token },
      () => {
        stompClient.current.subscribe(
          `/topic/room.${params.chatRoomId}`,
          (data) => {
            const newMessage = JSON.parse(data.body);
            addMessage(newMessage);
          },
          { headers: { Authorization: token } }
        );
      },
      (e) => {
        console.error(e);
      }
    );
  }, [contents]);

  const handleSubmit = (e) => {
    if (message.trim().length === 0) {
      return;
    }
    let token = localStorage.getItem("accessToken");
    const newMessage = {
      content: message,
    };
    stompClient.current.publish({
      destination: `/app/chat.message.${params.chatRoomId}`,
      headers: { Authorization: token },
      body: JSON.stringify(newMessage),
    });
    setMessage("");
    e.preventDefault();
  };
  const addMessage = (message) => {
    setContents((prev) =>
      prev.concat({
        email: message.email,
        nickname: message.nickname,
        content: message.content,
        createdAt: timeConvert(message.createdAt),
      })
    );
  };
  return (
    <div className="container">
      <Screen
        contents={contents}
        handleSubmit={handleSubmit}
        message={message}
        setMessage={setMessage}
        userId={userId}
      />
    </div>
  );
};
export default ChatContainer;
