// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";
import styles from './SongCard.module.css';
import Link from "next/link";
import {Song} from "@/types";
import { list } from 'postcss';



export default function SongCard(props: {song: Song}) {
    const song = props.song
    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.title}>{song.name}</div>
                <div className={styles.description}>{song.description}</div>
            </div>
            <div className={styles.meta}>
                <p className={styles.id}>id: {song.id}</p>
                <Link href={`/artists/${song.artist.id}`} className={styles.artist}>
                    {song.artist.name}
                </Link>
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