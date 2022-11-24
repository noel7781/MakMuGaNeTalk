import React, { useEffect, useState } from "react";
import { getChatRoomList } from "../apis/ChatRoomAPI";
import LockIcon from "@mui/icons-material/Lock";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import "../css/chatRoom.css";
import { useNavigate } from "react-router-dom";
const Groupchat = () => {
  const [chatRoomList, setChatRoomList] = useState([]);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPage, setTotalPage] = useState(0);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchData = async () => {
      const response = await getChatRoomList();
      setChatRoomList(response.data.chatRoomList);
      setCurrentPage(response.data.currentPageNumber);
      setTotalPage(response.data.totalPageNumber);
      return response;
    };
    fetchData();
  }, []);

  const handleJoinChatRoom = (id) => {
    navigate(`/chatRooms/${id}`);
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
                    <ThumbUpIcon />
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

export default Groupchat;
