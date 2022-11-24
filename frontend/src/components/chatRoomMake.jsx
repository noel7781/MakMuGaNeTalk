import React from "react";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import { useState } from "react";
import { Checkbox, TextField } from "@mui/material";
import { createChatRoom } from "../apis/ChatRoomAPI";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
  textAlign: "center",
};

const ChatRoomMake = () => {
  const [title, setTitle] = useState("");
  const [tag, setTag] = useState("");
  const [isPublic, setIsPublic] = useState(false);
  const [password, setPassword] = useState("");
  const [open, setOpen] = useState(false);

  const handleCreateChatRoom = async () => {
    const tagList = tag.split(",");
    const response = await createChatRoom(title, tagList, isPublic, password);
    handleClose();
  };

  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };
  return (
    <div>
      <Button onClick={() => handleOpen()}>채팅방 생성</Button>
      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Typography variant="h3" component="h1">
            채팅방 만들기
          </Typography>
          <div>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <Typography sx={{ mt: 2 }}>채팅방 이름</Typography>
              <TextField
                type="text"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
              />
            </div>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <Typography sx={{ mt: 2 }}>태그</Typography>
              <TextField
                type="text"
                value={tag}
                onChange={(e) => setTag(e.target.value)}
              />
            </div>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <Typography sx={{ mt: 2 }}>공개 여부</Typography>
              <Checkbox
                checked={isPublic}
                onChange={() => {
                  setIsPublic(() => !isPublic);
                }}
              />
            </div>
            {!isPublic ? (
              <div style={{ display: "flex", justifyContent: "space-between" }}>
                <Typography sx={{ mt: 2 }}>비밀번호</Typography>
                <TextField
                  type="password"
                  value={password}
                  onChange={(e) => setPassword(e.target.value)}
                />
              </div>
            ) : undefined}
          </div>
          <Button
            style={{
              display: "flex",
              justifyContent: "center",
              width: "100%",
              height: "100%",
            }}
            onClick={handleCreateChatRoom}
          >
            만들기
          </Button>
        </Box>
      </Modal>
    </div>
  );
};

export default ChatRoomMake;
