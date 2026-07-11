"use client"
import {Song, StreamingService} from "@/types";
import { useEffect, useState } from "react";
import {getAllSongs, getAllStreamingServices} from "@/repository/AnalyzerRepository";
import styles from "./page.module.css";
import StreamingServiceCard from "@/components/Cards/StreamingServiceCard/StreamingServiceCard";

export default function Page() {
    const [streamingServices, setStreamingServices] = useState<StreamingService[]>([]);
    
    useEffect(() => {
        getAllStreamingServices(setStreamingServices)
    }, [])
    
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Streaming Services</h1>
            <div className={styles.cardsGrid}>
                {streamingServices.map((service) =>
                    <StreamingServiceCard key={service.id} streamingService={service} />
                )}
            </div>
        </div>
    )
}