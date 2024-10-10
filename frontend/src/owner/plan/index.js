import React, { useEffect, useState } from "react";
import { BsDot, BsFillRocketTakeoffFill } from "react-icons/bs";
import { FaCheck, FaPaperPlane, FaTimes } from "react-icons/fa";
import { ImAirplane } from "react-icons/im";
import "../../static/css/pricing/pricingPage.css";

export default function PricingPlan () {

  const [plan, setPlan] = useState(null);
  const [owner, setOwner] = useState({});
  const [message, setMessage] = useState(null);  
  const jwt = JSON.parse(window.localStorage.getItem("jwt"));
  
  useEffect(()=>{ setUp();},[]);
  
  async function setUp(){
    const myowner = await (
      await fetch(`/api/v1/plan`, {
        headers: {
          Authorization: `Bearer ${jwt}`,
        },
      })
    ).json();
    if (myowner.message) setMessage(myowner.message);
    else{
      setPlan(myowner.clinic.plan);
      setOwner(myowner);
    } 
  }

  async function changePlan(event, plan) {
    event.preventDefault();

    await fetch("/api/v1/plan", {
      method: "PUT",
      headers: {
        Authorization: `Bearer ${jwt}`,
        Accept: "application/json",
        "Content-Type": "application/json",
      },
      body: JSON.stringify(plan),
    });
    window.location.href = "/plan";
  }

  
    
    if (message) {
      return <h2 className="text-center">{message}</h2>;
    }

    return (
      <div className="pricing-page-container">
        <div>
          <h1 className="pricing-title">My Plan - {plan}</h1>
        </div>
        <div className="section-pricing">
          <div className="pricing-container">
            <div className="pricing-card text-center">
              <div className="title">
                <div className="icon">
                  <FaPaperPlane color="white" />
                </div>
                <h2>BASIC</h2>
              </div>
              <div className="plan-price">
                <h4>FREE</h4>
              </div>
              {plan === "BASIC" ? (
                <button disabled> ACTIVE </button>
                ) : (
                <button onClick={(e) => changePlan(e, "BASIC")}> CHANGE </button>
                )}
            </div>
            {/* END Col one */}
            <div className="pricing-card text-center">
              <div className="title">
                <div className="icon">
                  <ImAirplane color="white" />
                </div>
                <h2>GOLD</h2>
              </div>
              <div className="plan-price">
                <h4>5</h4>

                <h5>€</h5>
              </div>
              {plan === "GOLD" ? (
                <button disabled> ACTIVE </button>
                ) : (
                <button onClick={(e) => changePlan(e, "GOLD")}> CHANGE </button>
                )}
            
            </div>
            {/* END Col two */}
            <div className="pricing-card text-center">
              <div
                className="title"
                style={{
                  display: "flex",
                  flexDirection: "column",
                  alignItems: "center",
                }}
              >
                <div className="icon">
                  <BsFillRocketTakeoffFill color="white" />
                </div>
                <h2>PLATINUM</h2>
              </div>
              <div className="plan-price">
                <h4>12</h4>

                <h5>€</h5>
              </div>
            {plan === "PLATINUM" ? (
                <button disabled> ACTIVE </button>
                ) : (
                <button onClick={(e) => changePlan(e, "PLATINUM")}> CHANGE </button>
                )}
            </div>
            {/* END Col three */}
          </div>
        </div>
      </div>
    );
}