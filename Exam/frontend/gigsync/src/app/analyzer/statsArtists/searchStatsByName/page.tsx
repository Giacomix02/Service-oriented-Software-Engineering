"use client"

import styles from "../statsArtists.module.css";
import {useState} from "react";
import {Artist, Song} from "@/types";
import SearchBar from "@/components/SearchBar/SearchBar";
import {getStatsArtistsByName} from "@/repository/AnalyzerRepository";
import ArtistCard from "@/components/Cards/ArtistCard/ArtistCard";
import SongCard from "@/components/Cards/SongCard/SongCard";


export default function Page() {
    const [artists, setArtists] = useState<Artist[] | undefined>()
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Artists by Name</h1>
            <SearchBar<Artist[]> searchFunction={getStatsArtistsByName} setter={setArtists} type={"text"}/>
            <div className={styles.cardsGrid}>
                { artists?.length === 0 && <p className={styles.noResults}>No results found</p> }
                {
                    artists?.map((artist) => (
                        <ArtistCard key={artist.id} artist={artist}/>
                    ))
                }
            </div>
        </div>
    )
}