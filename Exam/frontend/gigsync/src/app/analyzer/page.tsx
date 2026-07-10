import {Song} from "@/types";
import SongCard from "@/components/SongCard/SongCard";

export default function Page() {

    const song: Song = {id: 1, artist:{
            id: 1, description: "aa", name:"aaasf"
        }, description:"song", plays:213, title:"songtit"}

    return (
        <div>
            <h1>Analyzer</h1>
            <SongCard song={song}></SongCard>
        </div>
    )
}