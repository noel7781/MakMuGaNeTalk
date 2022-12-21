import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import SearchIcon from "@mui/icons-material/Search";
import Typography from "@mui/material/Typography";
import { useState } from "react";
import { TextField } from "@mui/material";
import { Button } from "@mui/material";
import { getChatRoomList } from "../apis/ChatRoomAPI";
import { useRef } from "react";

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

const Search = ({ setTags, setKeyword }) => {
  const [open, setOpen] = useState(false);

  const tagRef = useRef();
  const keywordRef = useRef();
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const handleSubmit = (e) => {
    setTags(tagRef.current.value);
    setKeyword(keywordRef.current.value);

    tagRef.current.value = "";
    keywordRef.current.value = "";
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
        <SearchIcon />
        <Button>검색</Button>
      </div>
      <Modal open={open} onClose={handleClose}>
        <Box sx={style}>
          <Typography
            variant="h3"
            component="h1"
            style={{ paddingBottom: "40px" }}
          >
            채팅방 검색
          </Typography>
          <div className="input--div">
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                marginBottom: "2rem",
              }}
            >
              <label htmlFor="keyword">키워드</label>
              <input
                type="text"
                name="keyword"
                placeholder="키워드"
                ref={keywordRef}
              />
            </div>
            <div
              style={{
                display: "flex",
                justifyContent: "center",
                marginBottom: "2rem",
              }}
            >
              <label htmlFor="tag">태그</label>
              <input
                type="text"
                name="tag"
                placeholder="태그(,로 구분)"
                ref={tagRef}
              />
            </div>
          </div>
          <button className="w-btn w-btn-blue" onClick={handleSubmit}>
            검색
          </button>
        </Box>
      </Modal>
    </>
  );
};

export default Search;
