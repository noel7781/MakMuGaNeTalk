import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import { useState } from "react";
import { TextField } from "@mui/material";
import { inviteChatRoom } from "../apis/ChatRoomAPI";

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 600,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
  textAlign: "center",
};

const ChatRoomInvite = () => {
  const [userNickname, setUserNickname] = useState("test");
  const [inviteMessage, setInviteMessage] = useState("hihiyo!");
  const [open, setOpen] = useState(false);

  const handleInviteChatRoom = async () => {
    const response = await inviteChatRoom(userNickname, inviteMessage);
    console.log(response);
    handleClose();
  };
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };
  return (
    <>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        }}
        onClick={() => handleOpen()}
      >
        <PersonAddIcon />
        <Button>초대</Button>
      </div>
      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Typography variant="h3" component="h1">
            1:1 채팅방 요청 보내기
          </Typography>
          <div>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <Typography sx={{ mt: 2 }}>유저 닉네임</Typography>
              <TextField
                type="text"
                value={userNickname}
                onChange={(e) => setUserNickname(e.target.value)}
              />
            </div>
            <div style={{ display: "flex", justifyContent: "space-between" }}>
              <Typography sx={{ mt: 2 }}>메시지</Typography>
              <TextField
                type="text"
                value={inviteMessage}
                onChange={(e) => setInviteMessage(e.target.value)}
              />
            </div>
          </div>
          <Button
            style={{
              display: "flex",
              justifyContent: "center",
              width: "100%",
              height: "100%",
            }}
            onClick={handleInviteChatRoom}
          >
            요청 보내기
          </Button>
        </Box>
      </Modal>
    </>
  );
};

export default ChatRoomInvite;
