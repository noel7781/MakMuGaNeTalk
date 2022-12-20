import React from "react";
import ChatRoomInvite from "./chatRoomInvite";
import ChatRoomMake from "./chatRoomMake";
import Search from "./ui/search";
import "../css/chatRoomHeader.css";

const ChatroomHeader = () => {
  return (
    <div className="chatpage--header">
      <Search />
      <ChatRoomInvite />
      <ChatRoomMake />
    </div>
  );
};

export default ChatroomHeader;
