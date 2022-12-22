import jwt_decode from "jwt-decode";
import Box from "@mui/material/Box";
import Button from "@mui/material/Button";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
import CheckIcon from "@mui/icons-material/Check";
import CloseIcon from "@mui/icons-material/Close";
import DriveFileRenameOutlineIcon from "@mui/icons-material/DriveFileRenameOutline";
import { useState } from "react";
import { TextField } from "@mui/material";
import { checkNicknameExist, changeNickname, reissue } from "../apis/AuthAPI";
import { useSelector } from "react-redux";

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
  const [open, setOpen] = useState(false);
  const [isDuplicate, setIsDuplicate] = useState(true);
  const [nickname, setNickname] = useState("");

  const { accessToken } = useSelector((state) => state.authToken);

  const handleChangeNickname = async () => {
    if (isDuplicate) {
      alert("중복 확인이 필요합니다.");
      return;
    }
    const userId = jwt_decode(accessToken).userId;
    const response = await changeNickname(userId, nickname);
    if (response.status === 200) {
      await reissue();
    }
    handleClose();
  };
  const handleTyping = (e) => {
    setNickname(e.target.value);
    setIsDuplicate(true);
  };
  const checkDuplicate = async () => {
    const response = await checkNicknameExist(nickname);
    if (response.status === 200) {
      if (response.data === 0) setIsDuplicate(false);
    }
  };

  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };
  return (
    <div>
      <DriveFileRenameOutlineIcon />
      <Button onClick={() => handleOpen()}>닉네임 변경</Button>
      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Typography variant="h3" component="h1">
            닉네임 변경하기
          </Typography>
          <div
            style={{
              display: "flex",
              justifyContent: "space-between",
              alignItems: "center",
            }}
          >
            <Typography>새 닉네임</Typography>
            <TextField type="text" value={nickname} onChange={handleTyping} />
            <Button onClick={checkDuplicate}>중복확인</Button>
            {isDuplicate ? (
              <CloseIcon style={{ color: "red" }} />
            ) : (
              <CheckIcon style={{ color: "green" }} />
            )}
          </div>
          <Button
            style={{
              display: "flex",
              justifyContent: "center",
              width: "100%",
              height: "100%",
            }}
            onClick={handleChangeNickname}
          >
            만들기
          </Button>
        </Box>
      </Modal>
    </div>
  );
};

export default ChatRoomMake;
