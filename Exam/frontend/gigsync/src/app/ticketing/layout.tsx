import React from 'react';
import TicketingNavbar from "@/components/TicketingNavbar/TicketingNavbar";

export default function TicketingLayout({
                                            children,
                                        }: {
    children: React.ReactNode;
}) {
    return (
        <div className="ticketing-container">
            <TicketingNavbar/>

            <main className="ticketing-content">
                {children}
            </main>
        </div>
    );
}