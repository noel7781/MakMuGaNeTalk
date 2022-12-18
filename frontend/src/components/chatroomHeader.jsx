import React from "react";
import ChatRoomInvite from "./chatRoomInvite";
import ChatRoomMake from "./chatRoomMake";
import SearchBar from "./ui/searchbar";
import "../css/chatRoomHeader.css";

const ChatroomHeader = () => {
  return (
    <div className="chatpage--header">
      <SearchBar />
      <ChatRoomInvite />
      <ChatRoomMake />
    </div>
  );
};

export default ChatroomHeader;
