import React, { useEffect, useState } from "react";
import { clickLikeButton, getChatRoomList } from "../apis/ChatRoomAPI";
import { useNavigate } from "react-router-dom";
import LockIcon from "@mui/icons-material/Lock";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import "../css/chatRoom.css";
const MyChat = () => {
  const [chatRoomList, setChatRoomList] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);
  const [tagList, setTagList] = useState([]);
  const [keyword, setKeyword] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      const response = await getChatRoomList(
        tagList,
        keyword,
        currentPage,
        true
      );
      if (response.status === 200) {
        setChatRoomList(response.data.chatRoomList);
        setCurrentPage(response.data.currentPageNumber);
        setTotalPage(response.data.totalPageNumber);
        return response;
      }
    };
    fetchData();
  }, []);

  const handleJoinChatRoom = (id) => {
    navigate(`/chatRooms/${id}`);
  };

  const handleLikeButton = async (id) => {
    const chatRoom = chatRoomList.find((room) => room.id === id);
    const current_like = chatRoom.myFavorite;
    const response = await clickLikeButton(id, current_like);
    if (response.status === 200) {
      const newChatRoom = { ...chatRoom };
      newChatRoom.likeCount += current_like === false ? 1 : -1;
      newChatRoom.myFavorite = !chatRoom.myFavorite;
      setChatRoomList((prev) => {
        return prev.map((room) => {
          if (room.id === id) {
            return newChatRoom;
          }
          return room;
        });
      });
    }
  };

  return (
    <div>
      {chatRoomList &&
        chatRoomList.map((room) => {
          return (
            <table key={room.id} className="chattable">
              <tbody className="chatbody">
                <tr className="chatrows">
                  <td className="chatid">{room.id}</td>
                  <td
                    className="chattitle"
                    onClick={() => handleJoinChatRoom(room.id)}
                  >
                    {room.title}
                  </td>
                  <td className="chattype">
                    {room.type === "PRIVATE_CHAT" ? <LockIcon /> : ""}
                  </td>
                  <td className="chatlike">
                    <ThumbUpIcon
                      className={`${room.myFavorite ? "favorite_room" : ""}`}
                      onClick={() => handleLikeButton(room.id)}
                    />
                    {room.likeCount}
                  </td>
                </tr>
              </tbody>
            </table>
          );
        })}
    </div>
  );
};

export default MyChat;
