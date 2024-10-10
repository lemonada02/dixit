import React from "react";
import { BsDot, BsFillRocketTakeoffFill } from "react-icons/bs";
import { FaCheck, FaPaperPlane, FaTimes } from "react-icons/fa";
import { ImAirplane } from "react-icons/im";
import "../../static/css/pricing/pricingPage.css";

export default function PlanList() {
  
    return (
      <div className="pricing-page-container">
        <div>
          <h1 className="pricing-title">Pricing Plans</h1>
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
            </div>
            {/* END Col two */}
            <div className="pricing-card text-center">
              <div className="title" style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                <div className="icon">
                  <BsFillRocketTakeoffFill color="white" />
                </div>
                <h2>PLATINUM</h2>
              </div>
              <div className="plan-price">
                <h4>12</h4>

                <h5>€</h5>
              </div>
            </div>
            {/* END Col three */}
          </div>
        </div>
      </div>
    );
  }
