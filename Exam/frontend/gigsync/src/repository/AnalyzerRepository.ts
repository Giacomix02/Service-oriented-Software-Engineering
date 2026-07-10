import { Song } from "@/types"
import axios from "axios"
import { config } from "process"
import { Dispatch, SetStateAction } from "react"

const baseURL= "/api-gateway"

/// api/analyze/songs
export const getAllSongs = async (setSongs: Dispatch<SetStateAction<Song[]>>) => {
    const response = await axios.get(baseURL+"/api/analyze/songs", )
    const songs: Song[] = await response.data
    setSongs(songs)
}

