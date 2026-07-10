"use client"
import {Song} from "@/types";
import SongCard from "@/components/SongCard/SongCard";
import { useEffect, useState } from "react";
import { getAllSongs } from "@/repository/AnalyzerRepository";

export default function Page() {
    const [songs, setSongs] = useState<Song[]>([]);
    useEffect(() => {
        getAllSongs(setSongs)
    }, [])
    
    const song: Song = {id: 1, artist:{
            id: 1, description: "aa", name:"aaasf"
        }, description:"song", plays:213, name:"songtit",
    streamingServices: [{name: "spotify", description:"this is spotify"}, {name:"YTMusic", description:"this is ytMusic"}]  }

    return (
        <div>
            <h1>Analyzer</h1>
            <SongCard song={song}></SongCard>
            {songs.map((song) => 
        <SongCard key={song.id} song={song} />
    )}
        </div>
       
    )
}