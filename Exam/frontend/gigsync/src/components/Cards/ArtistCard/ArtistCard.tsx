// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";
import { Artist } from '@/types';
import styles from '../Card.module.css';





export default function ArtistCard(props: {artist: Artist}) {
    const artist = props.artist
    return (
        <div className={styles.card}>
            <div className={styles.header}>
                <div className={styles.title}>{artist.name}</div>
                <div className={styles.description}>{artist.description}</div>
            </div>
            <div className={styles.meta}>
                <p className={styles.id}>id: {artist.id}</p>
            </div>
            
        </div>
    )
}