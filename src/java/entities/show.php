<?PHP
require("includes/header.php");

$sql_string = "SELECT * FROM KUNDE where ID_Kunde = '".$_SESSION['ID_Kunde']."'";

$stmnt = oci_parse($conn, $sql_string);

oci_execute($stmnt);
echo "<table><tr><th>ID</th><th>Benutzername</th><th>Art</th><th>Firma</th><th>Name</th><th>Vorname</th>
      <th>E-Mail</th><th>Strasse</th><th>PLZ</th><th>Ort</th><th>Geburtsdatum</th><th>Festnetznr.</th>
	  <th>Handynr.</th><th>Kreditkarte</th></tr>";
	  
while ($row=oci_fetch_assoc($stmnt))
{
	echo '<tr><td>'.$row['ID_KUNDE'].'</td>
          <td>'.$row['BENUTZERNAME'].'</td>
		  <td>'.$row['ART'].'</td>
		  <td>'.$row['FIRMA'].'</td>
		  <td>'.$row['NAME'].'</td>
		  <td>'.$row['VORNAMENAME'].'</td>
		  <td>'.$row['EMAIL'].'</td>
		  <td>'.$row['STRASSE'].'</td>
		  <td>'.$row['PLZ'].'</td>
          <td>'.$row['ORT'].'</td>
          <td>'.$row['GEB_DATUM'].'</td>
		  <td>'.$row['TEL_FESTNETZ'].'</td>
		  <td>'.$row['TEL_MOBIL'].'</td>
		  <td>'.$row['KREDITKARTE'].'</td></tr>';
}
echo "</table>";
require("includes/footer.php");
?>
