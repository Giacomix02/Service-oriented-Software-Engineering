import styles from "../songs.module.css";

export default function Page() {
    return (
        <div className={styles.pageContainer}>
            <h1 className={styles.pageTitle}>Search Song by Id</h1>
            <div className={styles.cardsGrid}>

            </div>
        </div>
    )
}