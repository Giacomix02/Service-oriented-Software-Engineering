// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";
import styles from './SongCard.module.css';
import Link from "next/link";
import {Song} from "@/types";

interface SongCardProps {
    song: Song;
}

export default function SongCard({ song }: SongCardProps) {
    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.title}>{song.title}</div>
                <div className={styles.description}>{song.description}</div>
            </div>
            <div className={styles.meta}>
                <p className={styles.id}>id: {song.id}</p>
                <Link href={`/artists/${song.artist.id}`} className={styles.artist}>
                    {song.artist.name}
                </Link>
            </div>
            <div className={styles.footer}>
                <span className={styles.plays}>{song.plays} plays</span>
            </div>
        </div>
    )
}