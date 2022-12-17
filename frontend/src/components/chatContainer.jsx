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
  const [userId, setUserId] = useState(0);
  const [joinUserList, setJoinUserList] = useState([]);
  const params = useParams();
  const dispatch = useDispatch();
  const stompClient = useRef(new CompatClient());
  const isConnected = useRef(false);

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
      setUserId(decodedToken["userId"]);
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
          stompClient.current.subscribe(
            `/topic/roomUsers.${params.chatRoomId}`,
            (data) => {
              const res = JSON.parse(data.body);
              setJoinUserList(res.userList);
            },
            { headers: { Authorization: token } }
          );
          isConnected.current = true;

          stompClient.current.publish({
            destination: `/app/chat.enter.${params.chatRoomId}`,
            headers: { Authorization: token },
            body: JSON.stringify({ content: "입장했습니다." }),
          });
        },
        (e) => {
          console.error(e);
        }
      );
    }
    const fetchData = async () => {
      const response = await getMessages(params.chatRoomId);
      if (response.status === 200) {
        const messages = response.data;
        messages.map((msg) => {
          msg.createdAt = timeConvert(msg.createdAt);
        });
        setContents(messages);
      }
    };
    fetchData();
    return () => {
      if (isConnected.current) {
        stompClient.current.publish({
          destination: `/app/chat.leave.${params.chatRoomId}`,
          headers: { Authorization: token },
        });
        stompClient.current.disconnect(() => {
          isConnected.current = false;
        });
      }
    };
  }, []);

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
        userId: message.userId,
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
        joinUserList={joinUserList}
      />
    </div>
  );
};
export default ChatContainer;
