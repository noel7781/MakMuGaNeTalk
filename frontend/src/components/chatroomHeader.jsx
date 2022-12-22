import React from "react";
import ChatRoomInvite from "./chatRoomInvite";
import ChatRoomMake from "./chatRoomMake";
import Search from "./search";
import "../css/chatRoomHeader.css";

const ChatroomHeader = (props) => {
  const { tags, setTags, keyword, setKeyword } = props;
  return (
    <div className="chatpage--header">
      <Search setTags={setTags} setKeyword={setKeyword} />
      <ChatRoomInvite />
      <ChatRoomMake />
    </div>
  );
};

export default ChatroomHeader;
