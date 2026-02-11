import { jwtDecode } from "jwt-decode";

export function getUserEmail(){
  const token = localStorage.getItem("token");
  if(!token) return null;

  const decoded = jwtDecode(token);
  return decoded.sub;   // email stored in token subject
}
