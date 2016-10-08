 <?PHP
 
require("includes/header.php");

$sql_string = "SELECT * FROM VERMIETUNG";
$stmnt = oci_parse($conn, $sql_string);

oci_execute($stmnt);
echo "<table><tr><th>ID Miete</th><th>Benutzername</th><th>Anmietung am</th><th>Rückgabe am</th><th>Anmiet Ort</th><th>Rückgabe Ort</th>
      <th>Gesamtpreis</th><th>Anzahlung</th><th>Bearbeiter ID</th><th>Kunden ID</th></th>";
	  
while ($row=oci_fetch_assoc($stmnt))
{
	echo '<tr><td>'.$row['ID_MIETE'].'</td>
                  <td>'.$row['BENUTZERNAME'].'</td>
		  <td>'.$row['ANMIETUNG_AM'].'</td>
		  <td>'.$row['RUECKGABE_AM'].'</td>
		  <td>'.$row['ANMIET_ORT'].'</td>
		  <td>'.$row['RUECKGABE_ORT'].'</td>
		  <td>'.$row['GESAMTPREIS'].'</td>
		  <td>'.$row['ANZAHLUNG'].'</td>
		  <td>'.$row['BEARBEITER_ID_BEARBEITER'].'</td>
                  <td>'.$row['KUNDE_ID_KUNDE'].'</td>';
                  
}
echo "</table>";
require("includes/footer.php");
?>

