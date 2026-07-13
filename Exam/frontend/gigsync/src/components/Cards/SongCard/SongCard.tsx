// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";
import styles from '../Card.module.css';
import Link from "next/link";
import {Song} from "@/types";



export default function SongCard(props: {song: Song | undefined}) {
    const song = props.song
    if (song === undefined || song.id === undefined) return (
        <div className={styles.card}>No song found</div>
    )
    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.title}>{song.name}</div>
                <div className={styles.description}>{song.description}</div>
            </div>
            <div className={styles.meta}>
                <p className={styles.id}>id: {song.id}</p>
                <a className={styles.artist}>
                    {song.artist.name}
                </a>
            </div>
            <div className={styles.footer}>
                {song.streamingServices && 
                    song.streamingServices.map( (srtService) =>
                    <span key={srtService.name} className={styles.streamingService}>{srtService.name}</span>
                )
                    }
                <span className={styles.plays}>{song.views} plays</span>
            </div>
        </div>
    )
}