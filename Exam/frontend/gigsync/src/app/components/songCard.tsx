// import type { AnchorHTMLAttributes, ComponentType, ReactNode } from "react";

import { Card, Link as HLink } from "@heroui/react";
// import styles from './songCard.module.css';

// const HeroLink = HLink as ComponentType<AnchorHTMLAttributes<HTMLAnchorElement> & { children?: ReactNode; href?: string }>;

export interface Song {
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

export default function SongCard(props: {song: Song}) {
    const song = props.song;
    return (
        <Card className="w-[400px]"
        // className={styles.size}
        >
            <Card.Header>
                <Card.Title>{song.title}</Card.Title>

                <Card.Description>{song.description}</Card.Description>
            </Card.Header>
            <Card.Content>
                <p>id: {song.id}</p>
                {/* <HLink href={`/artists/${song.artist.id}`}>{song.artist.name}</HLink> */}
            </Card.Content>
            <Card.Footer>{song.plays} plays</Card.Footer>
        </Card>
    )
}