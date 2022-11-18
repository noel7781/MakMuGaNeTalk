import React, { useState } from "react";
import MessageIcon from "@mui/icons-material/Message";
import GroupsIcon from "@mui/icons-material/Groups";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";
import ChatRoomInvite from "../components/chatRoomInvite";
import ChatRoomMake from "../components/chatRoomMake";

const Main = () => {
  const navigate = useNavigate();
  const [open, setOpen] = useState(false);
  const [open2, setOpen2] = useState(false);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };
  const handleOpen2 = () => {
    setOpen2(true);
  };
  const handleClose2 = () => {
    setOpen2(false);
  };
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
      <Button onClick={() => handleOpen()}>초대버튼</Button>
      <ChatRoomInvite open={open} handleClose={handleClose} />
      <Button onClick={() => handleOpen2()}>채팅방 생성</Button>
      <ChatRoomMake open={open2} handleClose={handleClose2} />
    </div>
  );
};

export default Main;
