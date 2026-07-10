// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";
import styles from './songCard.module.css';
import Link from "next/link";

// const HeroLink = HLink as ComponentType<AnchorHTMLAttributes<HTMLAnchorElement> & { children?: ReactNode; href?: string }>;

interface Song {
    id: number;
    title: string;
    description: string;
    plays: number;
    artist: Artist;
};

interface Artist {
    id: number;
    name: string;
    description: string;
}

// export default function SongCard(song: Song) {
//     return (
//         <div className={styles.size}>
//             <div>
//                 <div>{song.title}</div>
//
//                 <div>{song.description}</div>
//             </div>
//             <div>
//                 <p>id: {song.id}</p>
//                 <Link href={`/artists/${song.artist.id}`}>{song.artist.name}</Link>
//             </div>
//             <div>{song.plays} plays</div>
//         </div>
//     )
// }

export default function SongCard(song: Song) {
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