function eliminarPaciente(id) {
    if (confirm('¿Estás seguro de eliminar este paciente?')) {
        fetch(`http://localhost:8080/pacientes/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Paciente eliminado exitosamente.');
                // Actualizar la lista de pacientes después de eliminar
                document.getElementById('listar-pacientes').click();
            } else {
                throw new Error('Error al eliminar el paciente.');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
            alert('Hubo un problema al eliminar el paciente. Por favor, inténtelo nuevamente.');
        });
    }
}
function eliminarOdontologo(id) {
    if (confirm('¿Estás seguro de eliminar este odontologo?')) {
        fetch(`http://localhost:8080/odontologos/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Odontologo eliminado exitosamente.');
                // Actualizar la lista de pacientes después de eliminar
                document.getElementById('listar-odontologos').click();
            } else {
                throw new Error('Error al eliminar el paciente.');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
            alert('Hubo un problema al eliminar el odontologo. Por favor, inténtelo nuevamente.');
        });
    }
}
function eliminarTurno(id) {
    if (confirm('¿Estás seguro de eliminar este turno?')) {
        fetch(`http://localhost:8080/turnos/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alert('Turno eliminado exitosamente.');
                // Actualizar la lista de turnos después de eliminar
                document.getElementById('listar-turnos').click();
            } else {
                throw new Error('Error al eliminar el turno.');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
            alert('Hubo un problema al eliminar el turno. Por favor, inténtelo nuevamente.');
        });
    }
}

 
   
   document.addEventListener('DOMContentLoaded',() => {
    const contentDiv = document.getElementById('content');

    
    // Menús desplegables
    document.getElementById('toggle-pacientes').addEventListener('click', () => {
        toggleMenu('pacientes-menu');
    });

    document.getElementById('toggle-odontologos').addEventListener('click', () => {
        toggleMenu('odontologos-menu');
    });

    document.getElementById('toggle-turnos').addEventListener('click', () => {
        toggleMenu('turnos-menu');
    });

    function toggleMenu(menuId) {
        const menu = document.getElementById(menuId);
        menu.classList.toggle('hidden');
    };

    
    

   
    // Evento para registrar un paciente
    document.getElementById('registrar-paciente').addEventListener('click', () => {
        contentDiv.innerHTML = `
            <h2>Registrar Paciente</h2>
            <form id="patient-form">
                <label for="nombre">Nombre:</label>
                <input type="text" id="nombre" name="nombre" required>
                <label for="apellido">Apellido:</label>
                <input type="text" id="apellido" name="apellido" required>
                <label for="dni">DNI:</label>
                <input type="number" id="dni" name="dni" required>
                
                <label for="fechaIngreso">Fecha Ingreso (yyyy-mm-dd):</label>
                <input type="text" id="fechaIngreso" name="fechaIngreso" required placeholder="yyyy-mm-dd">
    

                <!-- Campos de domicilio -->
                <label for="calle">Calle:</label>
                <input type="text" id="calle" name="domicilio.calle" required>
                
                <label for="numero">Número:</label>
                <input type="text" id="numero" name="domicilio.numero" required>
                
                <label for="localidad">Localidad:</label>
                <input type="text" id="localidad" name="domicilio.localidad" required>
                
                <label for="provincia">Provincia:</label>
                <input type="text" id="provincia" name="domicilio.provincia" required>

                <button type="submit">Registrar</button>
            </form>
        `;

        document.getElementById('patient-form').addEventListener('submit', (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
        
            // Construir el objeto paciente incluyendo domicilio
            const patient = {
                nombre: formData.get('nombre'),
                apellido: formData.get('apellido'),
                dni: parseInt(formData.get('dni')),
                fechaIngreso: formData.get('fechaIngreso'),
                domicilioEntradaDto: {
                    calle: formData.get('domicilio.calle'),
                    numero: formData.get('domicilio.numero'),
                    localidad: formData.get('domicilio.localidad'),
                    provincia: formData.get('domicilio.provincia')
                }
            };
        
            // Envío del paciente al backend
            fetch('http://localhost:8080/pacientes/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(patient)
            })
            .then(response => {
                if (response.ok) {
                    alert('Paciente registrado exitosamente.');
                    e.target.reset();
                } else {
                    throw new Error('Error al registrar el paciente.');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
                alert('Hubo un problema al registrar el paciente. Por favor, inténtelo nuevamente.');
            });
        });
    });



    
    
    
        // Listar pacientes
        document.getElementById('listar-pacientes').addEventListener('click', () => {
            fetch('http://localhost:8080/pacientes/listar')
                .then(response => {
                    if (response.ok) {
                        return response.json();
                    } else {
                        throw new Error('Error al obtener la lista de pacientes.');
                    }
                })
                .then(pacientes => {
                    const tableRows = pacientes.map((paciente, index) => `
                        <tr>
                            <td>${paciente.id}</td>
                            <td>${paciente.nombre}</td>
                            <td>${paciente.apellido}</td>
                            <td>${paciente.dni}</td>
                            <td>${formatDate(paciente.fechaIngreso)}</td>
                            <td>${paciente.domicilioSalidaDto ? paciente.domicilioSalidaDto.calle : '-'}</td>
                            <td>${paciente.domicilioSalidaDto ? paciente.domicilioSalidaDto.numero : '-'}</td>
                            <td>${paciente.domicilioSalidaDto ? paciente.domicilioSalidaDto.localidad : '-'}</td>
                            <td>${paciente.domicilioSalidaDto ? paciente.domicilioSalidaDto.provincia : '-'}</td>
                            <td>
                                <button onclick="eliminarPaciente(${paciente.id})">Eliminar</button>

                            </td>
                        </tr>
                    `).join('');
    
                    const listadoHTML = `
                        <h2>Listado de Pacientes</h2>
                        <table>
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre</th>
                                    <th>Apellido</th>
                                    <th>DNI</th>
                                    <th>Fecha Ingreso</th>
                                    <th>Calle</th>
                                    <th>Número</th>
                                    <th>Localidad</th>
                                    <th>Provincia</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${tableRows}
                            </tbody>
                        </table>
                    `;
    
                    contentDiv.innerHTML = listadoHTML;
                })
                .catch(error => {
                    console.error('Error en la solicitud:', error);
                    alert('Hubo un problema al obtener la lista de pacientes. Por favor, inténtelo nuevamente.');
                });
        });
    
    
   
    
    
    
    
    function formatDate(dateString) {
        const date = new Date(dateString);
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0');
        const year = date.getFullYear();
        return `${day}/${month}/${year}`;
    }
    
    



    

    // Formulario de registro de odontólogos
    document.getElementById('registrar-odontologo').addEventListener('click', () => {
        contentDiv.innerHTML = `
            <h2>Registrar Odontólogo</h2>
            <form id="odontologo-form">
                <label for="matricula">Número de Matrícula:</label>
                <input type="text" id="matricula" name="matricula" required>
                <label for="odontologo-name">Nombre:</label>
                <input type="text" id="odontologo-name" name="nombre" required> <!-- Corregido: name="nombre" -->
                <label for="odontologo-surname">Apellido:</label>
                <input type="text" id="odontologo-surname" name="apellido" required> <!-- Corregido: name="apellido" -->
                <button type="submit">Registrar</button>
            </form>
        `;
    
        document.getElementById('odontologo-form').addEventListener('submit', (e) => {
            e.preventDefault();
            const formData = new FormData(e.target);
            const odontologo = {
                numeroMatricula: formData.get('matricula'),
                nombre: formData.get('nombre'), // Corregido: nombre del campo
                apellido: formData.get('apellido') // Corregido: nombre del campo
            };
    
            fetch('http://localhost:8080/odontologos/registrar', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(odontologo)
            })
            .then(response => {
                if (response.ok) {
                    alert('Odontólogo registrado exitosamente.');
                    e.target.reset();
                } else {
                    throw new Error('Error al registrar el odontólogo.');
                }
            })
            .catch(error => {
                console.error('Error en la solicitud:', error);
                alert('Hubo un problema al registrar el odontólogo. Por favor, inténtelo nuevamente.');
            });
        });
    
    });
    

    // Listar odontólogos
    document.getElementById('listar-odontologos').addEventListener('click', () => {
        fetch('http://localhost:8080/odontologos/listar')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al obtener la lista de odontologos.');
            }
        })
        .then(odontologos => {
            const tableRows = odontologos.map((odontologo, index) => `
            <tr>
                            <td>${odontologo.id}</td>
                            <td>${odontologo.numeroMatricula}</td>
                            <td>${odontologo.nombre}</td>
                            <td>${odontologo.apellido}</td>
                            <td>
                            <button onclick="eliminarOdontologo(${odontologo.id})">Eliminar</button>

                            </td>
                        </tr>
                    `).join('');

                    const listadoHTML = `

            <h2>Listado de Odontólogos</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Matrícula</th>
                        <th>Nombre</th>
                        <th>Apellido</th>
                        
                    </tr>
                </thead>
                <tbody>
                ${tableRows}
                </tbody>
            </table>
        `;

        contentDiv.innerHTML = listadoHTML;
    })
    .catch(error => {
        console.error('Error en la solicitud:', error);
        alert('Hubo un problema al obtener la lista de odontologos. Por favor, inténtelo nuevamente.');
    });
});

document.getElementById('registrar-turno').addEventListener('click', () => {
    contentDiv.innerHTML = `
        <h2>Registrar Turno</h2>
        <form id="turno-form">
            <label for="paciente-id">ID del Paciente:</label>
            <input type="text" id="paciente-id" name="pacienteId" required>
            <label for="odontologo-id">ID del Odontólogo:</label>
            <input type="text" id="odontologo-id" name="odontologoId" required>
            <label for="fechaYHora">Fecha y Hora (yyyy-mm-dd hh:mm:ss):</label>
            <input type="text" id="fechaYHora" name="fechaYHora" required placeholder="yyyy-mm-dd hh:mm:ss">
    
            <button type="submit">Registrar</button>
        </form>
    `;
    
    document.getElementById('turno-form').addEventListener('submit', (e) => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const pacienteId = formData.get('pacienteId');
        const odontologoId = formData.get('odontologoId');
        let fechaYHora = formData.get('fechaYHora');

        // Validar formato de fechaYHora (yyyy-mm-dd hh:mm:ss)
        const dateTimeRegex = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
        if (!dateTimeRegex.test(fechaYHora)) {
            alert('El formato de fecha y hora debe ser yyyy-mm-dd hh:mm:ss');
            return;
        }

        // Construir objeto turno con los datos requeridos
        const turno = {
            paciente: parseInt(pacienteId),
            odontologo: parseInt(odontologoId),
            fechaYHora: fechaYHora
        };

        fetch('http://localhost:8080/turnos/registrar', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(turno)
        })
        .then(response => {
            if (response.ok) {
                alert('Turno registrado exitosamente.');
                e.target.reset();
            } else {
                throw new Error('Error al registrar el turno.');
            }
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
            alert('Hubo un problema al registrar el turno. Por favor, inténtelo nuevamente.');
        });
    });

});

document.getElementById('listar-turnos').addEventListener('click', () => {
    fetch('http://localhost:8080/turnos/listar')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error al obtener la lista de turnos.');
            }
        })
        .then(turnos => {
            const tableRows = turnos.map((turno, index) => `
                <tr>
                    <td>${turno.id}</td>
                    <td>${turno.paciente.nombre} ${turno.paciente.apellido}</td>
                    <td>${turno.odontologo.nombre} ${turno.odontologo.apellido}</td>
                    <td>${formatDate(turno.fechaYHora)}</td>
                    <td>
                        <button onclick="eliminarTurno(${turno.id})">Eliminar</button>
                    </td>
                </tr>
            `).join('');

            const listadoHTML = `
                <h2>Listado de Turnos</h2>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Paciente</th>
                            <th>Odontólogo</th>
                            <th>Fecha y Hora</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${tableRows}
                    </tbody>
                </table>
            `;

            contentDiv.innerHTML = listadoHTML;
        })
        .catch(error => {
            console.error('Error en la solicitud:', error);
            alert('Hubo un problema al obtener la lista de turnos. Por favor, inténtelo nuevamente.');
        });
});

   });
   
   

    