import ChatRoomInvite from "../components/chatRoomInvite";
import ChatRoomMake from "../components/chatRoomMake";
import NicknameChange from "../components/nicknameChange";
import MessageIcon from "@mui/icons-material/Message";
import GroupsIcon from "@mui/icons-material/Groups";
import Card from "../components/ui/card";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import "../css/main.css";

const Main = () => {
  const navigate = useNavigate();
  return (
    <Card>
      <div style={{ display: "flex" }}>
        <div className="div--icon" onClick={() => navigate("/my-chat")}>
          <label name="my-chat">My Talk</label>
          <MessageIcon style={{ fontSize: "100px" }} />
        </div>
        <div className="div--icon" onClick={() => navigate("/group-chat")}>
          <label name="group-chat">All Talk</label>
          <GroupsIcon style={{ fontSize: "100px" }} />
        </div>
        <ChatRoomInvite />
        <ChatRoomMake />
        <NicknameChange />
      </div>
    </Card>
  );
};

export default Main;
