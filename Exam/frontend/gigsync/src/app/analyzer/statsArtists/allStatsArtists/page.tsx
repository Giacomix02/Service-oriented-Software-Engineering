"use client"
import {Artist, Song} from "@/types";
import { useEffect, useState } from "react";
import {getAllArtists, getAllSongs} from "@/repository/AnalyzerRepository";
import SongCard from "@/components/Cards/SongCard/SongCard";
import styles from "../statsArtists.module.css";
import ArtistCard from "@/components/Cards/ArtistCard/ArtistCard";

export default function Page() {
    const [artists, setArtists] = useState<Artist[]>([]);
    
    useEffect(() => {
        getAllArtists(setArtists)
    }, [])
    
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>All Artist</h1>
            <div className={styles.cardsGrid}>
                {artists.map((artist) =>
                    <ArtistCard key={artist.id} artist={artist} />
                )}
            </div>
        </div>
    )
}