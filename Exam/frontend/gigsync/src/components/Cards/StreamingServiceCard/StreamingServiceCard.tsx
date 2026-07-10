// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";
import styles from '../Card.module.css';
import { StreamingService } from "@/types";



export default function StreamingServiceCard(props: { streamingService: StreamingService }) {
    const streamingService = props.streamingService
    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.title}>{streamingService.name}</div>
                <div className={styles.description}>{streamingService.description}</div>
            </div>
        </div>
    )
}