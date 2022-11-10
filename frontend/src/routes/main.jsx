import React, { useState } from "react";
import MessageIcon from "@mui/icons-material/Message";
import GroupsIcon from "@mui/icons-material/Groups";
import { useNavigate } from "react-router-dom";
import { Button } from "@mui/material";
import ChatRoomMake from "../components/chatRoomMake";

const Main = () => {
  const navigate = useNavigate();

  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
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
      <Button onClick={handleOpen}>Open modal</Button>
      <ChatRoomMake open={open} handleClose={handleClose} />
    </div>
  );
};

export default Main;
