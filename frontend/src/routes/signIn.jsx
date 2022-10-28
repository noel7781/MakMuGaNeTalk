import { redirect } from "react-router-dom";
import { signIn } from "../apis/AuthAPI";

export async function action({ request }) {
  const formData = await request.formData();
  const { email, password } = Object.fromEntries(formData);
  const response = await signIn(email, password);
  if (response.status !== 200) {
    return redirect("/");
  }
  return redirect("/main");
}
