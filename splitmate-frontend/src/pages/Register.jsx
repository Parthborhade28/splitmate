import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

function Register(){

  const [name,setName]=useState("");
  const [email,setEmail]=useState("");
  const [password,setPassword]=useState("");
  const nav = useNavigate();

  const register = async ()=>{
    const res = await API.post("/auth/register",{name,email,password});
    localStorage.setItem("token",res.data.token);
    nav("/dashboard");
  };

  return(
    <div className="flex h-screen justify-center items-center bg-gray-100">
      <div className="bg-white p-8 rounded shadow w-80">
        <h1 className="text-2xl mb-4 font-bold">Register</h1>

        <input className="border p-2 w-full mb-3"
          placeholder="Name"
          onChange={e=>setName(e.target.value)}/>

        <input className="border p-2 w-full mb-3"
          placeholder="Email"
          onChange={e=>setEmail(e.target.value)}/>

        <input type="password"
          className="border p-2 w-full mb-3"
          placeholder="Password"
          onChange={e=>setPassword(e.target.value)}/>

        <button onClick={register}
          className="bg-green-500 text-white w-full p-2 rounded">
          Register
        </button>
      </div>
    </div>
  );
}

export default Register;
