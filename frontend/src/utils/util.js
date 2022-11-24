export const timeConvert = (times) => {
  return new Date(...times.slice(0, -1)).toLocaleString("ko");
};
