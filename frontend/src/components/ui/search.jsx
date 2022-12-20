import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import SearchIcon from "@mui/icons-material/Search";
import Typography from "@mui/material/Typography";
import { useState } from "react";
import { TextField } from "@mui/material";
import { Button } from "@mui/material";

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

const Search = () => {
  const [keywordInput, setKeywordInput] = useState("");
  const [tagInput, setTagInput] = useState("");
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
  };

  const keywordInputHandler = (e) => {
    setKeywordInput(e.target.value);
  };
  const tagInputHandler = (e) => {
    setTagInput(e.target.value);
  };
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(e);
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
                value={keywordInput}
                onChange={keywordInputHandler}
                placeholder="키워드"
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
                value={tagInput}
                onChange={tagInputHandler}
                placeholder="태그(,로 구분)"
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
