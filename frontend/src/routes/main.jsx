import ChatRoomInvite from "../components/chatRoomInvite";
import ChatRoomMake from "../components/chatRoomMake";
import NicknameChange from "../components/nicknameChange";
import MessageIcon from "@mui/icons-material/Message";
import GroupsIcon from "@mui/icons-material/Groups";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

const Main = () => {
  const navigate = useNavigate();
  return (
    <div style={{ display: "flex", justifyContent: "space-evenly" }}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
        }}
        onClick={() => navigate("/my-chat")}
      >
        <label name="my-chat">My Chat</label>
        <MessageIcon fontSize="large" />
      </div>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
        }}
        onClick={() => navigate("/group-chat")}
      >
        <label name="group-chat">Group Chat</label>
        <GroupsIcon fontSize="large" />
      </div>
      <ChatRoomInvite />
      <ChatRoomMake />
      <NicknameChange />
    </div>
  );
};

export default Main;
