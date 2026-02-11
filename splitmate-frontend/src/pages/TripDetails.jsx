import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import API from "../services/api";
import Navbar from "../components/Navbar";

export default function TripDetails() {

  const { id } = useParams();

  const [members, setMembers] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [settlement, setSettlement] = useState([]);

  const [memberEmail, setMemberEmail] = useState("");
  const [paidBy, setPaidBy] = useState("");
  const [amount, setAmount] = useState("");
  const [desc, setDesc] = useState("");

  // 🔹 Load members with names
  const fetchMembers = async () => {
    const memRes = await API.get(`/trips/${id}/members`);
    const memberIds = memRes.data;

    const users = await Promise.all(
      memberIds.map(m => API.get(`/users/${m.userId}`))
    );

    setMembers(users.map(u => u.data));
  };

  const fetchExpenses = async () => {
    const res = await API.get(`/expenses/${id}`);
    setExpenses(res.data);
  };

  const fetchData = async () => {
    await fetchMembers();
    await fetchExpenses();
  };

  // 🔹 Add member using email
  const addMember = async () => {
    try {
      const res = await API.get(`/users/by-email?email=${memberEmail}`);
      await API.post(`/trips/${id}/add-member?userId=${res.data.id}`);
      setMemberEmail("");
      fetchMembers();
    } catch (err) {
      alert("User not found! Ask them to register first.");
    }
  };


  // 🔹 Add expense using dropdown
  const addExpense = async () => {

    // 🔴 VALIDATION
    if (!paidBy) {
      alert("Please select who paid");
      return;
    }

    if (!amount || amount <= 0) {
      alert("Please enter valid amount");
      return;
    }

    if (!desc) {
      alert("Please enter description");
      return;
    }

    const userIds = members.map(m => m.id);

    await API.post(
      `/expenses?tripId=${id}&paidBy=${paidBy}&amount=${amount}&desc=${desc}`,
      userIds
    );

    // clear fields after success
    setAmount("");
    setDesc("");
    setPaidBy("");

    fetchExpenses();
  };


  const getSettlement = async () => {
    const res = await API.get(`/settle/${id}`);
    setSettlement(res.data);
  };
  const resetTrip = async ()=>{
    if(!confirm("Delete all expenses for this trip?")) return;
  
    await API.delete(`/expenses/trip/${id}`);
    setExpenses([]);
    setSettlement([]);
  };
  
  useEffect(() => { fetchData(); }, []);

  return (
    <div>
      <Navbar />
      <div className="p-10">

        <h1 className="text-3xl font-bold mb-6">Trip Details</h1>

        {/* Add Member */}
        <div className="mb-6">
          <h2 className="font-bold mb-2">Add Member</h2>
          <input
            className="border p-2 mr-2"
            placeholder="Enter user email"
            value={memberEmail}
            onChange={e => setMemberEmail(e.target.value)}
          />
          <button onClick={addMember}
            className="bg-green-500 text-white px-4">
            Add Member
          </button>
        </div>

        {/* Add Expense */}
        <div className="mb-6">
          <h2 className="font-bold mb-2">Add Expense</h2>

          <select
            className="border p-2 mr-2"
            onChange={e => setPaidBy(e.target.value)}
          >
            <option>Select payer</option>
            {members.map(m => (
              <option key={m.id} value={m.id}>{m.name}</option>
            ))}
          </select>

          <input
            type="number"
            className="border p-2 mr-2"
            placeholder="Amount"
            value={amount}
            onChange={e => setAmount(e.target.value)}
          />


          <input
            className="border p-2 mr-2"
            placeholder="Description"
            onChange={e => setDesc(e.target.value)}
          />

          <button onClick={addExpense}
            className="bg-blue-500 text-white px-4">
            Add Expense
          </button>
        </div>

        {/* Expenses List */}
        <h2 className="text-xl font-bold mb-2">Expenses</h2>
        {expenses.map(e => (
          <div key={e.id} className="bg-gray-100 p-2 mb-2">
            ₹{e.amount} — {e.description}
          </div>
        ))}

       {/* Settlement Buttons */}
<div className="mt-6 mb-4">
  <button
    onClick={getSettlement}
    className="bg-purple-600 text-white px-4 py-2 mr-3">
    Calculate Settlement
  </button>

  <button
    onClick={resetTrip}
    className="bg-red-500 text-white px-4 py-2">
    Reset Trip ❌
  </button>
</div>


        {settlement.map((s, i) => (
          <div key={i} className="bg-yellow-100 p-3 mb-2 rounded">
            <b>{s.fromUser}</b> pays <b>{s.toUser}</b> ₹{s.amount}
          </div>
        ))}

      </div>
    </div>
  );
}
