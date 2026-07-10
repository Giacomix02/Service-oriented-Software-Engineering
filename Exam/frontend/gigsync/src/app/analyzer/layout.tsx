import React from 'react';
import AnalyzerNavbar from "@/components/AnalyzerNavbar/AnalyzerNavbar";

export default function AnalyzerLayout({
                                           children,
                                       }: {
    children: React.ReactNode;
}) {
    return (
        <div className="analyzer-container">
            <AnalyzerNavbar/>

            <main className="analyzer-content">
                {children}
            </main>
        </div>
    );
}