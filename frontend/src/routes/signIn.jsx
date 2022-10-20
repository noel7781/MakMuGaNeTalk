import { redirect } from "react-router-dom";
import { signIn } from "../apis/AuthAPI";

export async function action({ request }) {
  const formData = await request.formData();
  const { email, password } = Object.fromEntries(formData);
  await signIn(email, password);
  return redirect("/main");
}
