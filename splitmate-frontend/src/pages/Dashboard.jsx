import { useEffect, useState } from "react";
import API from "../services/api";
import Navbar from "../components/Navbar";
import { useNavigate } from "react-router-dom";
import { getUserEmail } from "../context/Auth";

export default function Dashboard(){

  const [trips,setTrips] = useState([]);
  const [tripName,setTripName] = useState("");
  const [userId,setUserId] = useState(null);

  const nav = useNavigate();

  // 🔹 Load logged-in user from JWT
  const loadUser = async ()=>{
    const email = getUserEmail();
    const res = await API.get(`/users/by-email?email=${email}`);
    setUserId(res.data.id);
  };

  // 🔹 Get my trips
  const fetchTrips = async ()=>{
    if(!userId) return;
    const res = await API.get(`/trips/my?userId=${userId}`);
    setTrips(res.data);
  };

  // 🔹 Create trip
  const createTrip = async ()=>{
    await API.post(`/trips?name=${tripName}&userId=${userId}`);
    setTripName("");
    fetchTrips();
  };

  // Load user once
  useEffect(()=>{ loadUser(); },[]);

  // Load trips after user loaded
  useEffect(()=>{
    if(userId) fetchTrips();
  },[userId]);

  return(
    <div>
      <Navbar/>
      <div className="p-10">

        <h1 className="text-3xl font-bold mb-6">Your Trips ✈️</h1>

        <div className="flex gap-2 mb-6">
          <input
            className="border p-2"
            value={tripName}
            onChange={e=>setTripName(e.target.value)}
            placeholder="Trip name"
          />

          <button
            onClick={createTrip}
            className="bg-blue-500 text-white px-4 rounded">
            Create Trip
          </button>
        </div>

        <div className="grid grid-cols-3 gap-4">
          {trips.map(t=>(
            <div key={t.id}
              onClick={()=>nav(`/trip/${t.id}`)}
              className="bg-white p-4 rounded shadow cursor-pointer hover:bg-gray-50">
              <h2 className="text-xl font-bold">{t.name}</h2>
              <p className="text-sm text-gray-500">Trip ID: {t.id}</p>
            </div>
          ))}
        </div>

      </div>
    </div>
  );
}
