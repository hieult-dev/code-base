import { useEffect, useState } from 'react'
import { Button } from 'primereact/button'
import { Toolbar } from 'primereact/toolbar'
import { DataTable } from 'primereact/datatable'
import { Column } from 'primereact/column'
import CourseForm from './CourseForm'
import '@/apps/course/style/CourseManage.css'
import { fetchCoursesForAdmin } from '@/apps/admin/api/course-api-rest'

export default function CourseManage() {
    const [courses, setCourses] = useState([])
    const [loading, setLoading] = useState(false)

    const [showForm, setShowForm] = useState(false)
    const [selectedId, setSelectedId] = useState(null)
    const [mode, setMode] = useState('create') // create | update | detail

    useEffect(() => {
        loadCourses()
    }, [])

    const loadCourses = async () => {
        try {
            setLoading(true)
            const res = await fetchCoursesForAdmin()
            setCourses(res)
        } finally {
            setLoading(false)
        }
    }

    const openCreate = () => {
        setMode('create')
        setSelectedId(null)
        setShowForm(true)
    }

    return (
        <div className="page-wrapper">
            <div className="page-card">

                {/* ===== TOOLBAR ===== */}
                <div className="page-toolbar">
                    <Toolbar
                        left={() => (
                            <h3 className="m-0">Course Management</h3>
                        )}
                        right={() => (
                            <Button
                                label="Create"
                                icon="pi pi-plus"
                                onClick={openCreate}
                            />
                        )}
                    />
                </div>

                {/* ===== TABLE ===== */}
                <div className="page-content">
                    <DataTable
                        value={courses}
                        loading={loading}
                        dataKey="id"
                        rowHover
                        stripedRows
                        responsiveLayout="scroll"
                        className="p-datatable-sm"
                    >
                        <Column
                            header="#"
                            body={(_, opt) => opt.rowIndex + 1}
                            style={{ width: '60px' }}
                        />
                        <Column field="title" header="Title" />
                        <Column field="description" header="Description" />
                        <Column
                            field="price"
                            header="Price"
                            body={(row) => row.price?.toLocaleString()}
                        />
                        <Column
                            field="active"
                            header="Active"
                            body={(row) => (row.active ? 'Yes' : 'No')}
                        />
                        <Column
                            header="Action"
                            body={(row) => (
                                <div className="flex gap-2">
                                    <Button
                                        icon="pi pi-eye"
                                        rounded
                                        outlined
                                        onClick={() => {
                                            setSelectedId(row.id)
                                            setMode('detail')
                                            setShowForm(true)
                                        }}
                                    />
                                    <Button
                                        icon="pi pi-pencil"
                                        rounded
                                        outlined
                                        onClick={() => {
                                            setSelectedId(row.id)
                                            setMode('update')
                                            setShowForm(true)
                                        }}
                                    />
                                </div>
                            )}
                        />
                    </DataTable>
                </div>
            </div>

            {/* ===== FORM ===== */}
            {showForm && (
                <CourseForm
                    id={selectedId}
                    mode={mode}
                    visible={showForm}
                    onClose={() => setShowForm(false)}
                    onSuccess={loadCourses}
                />
            )}
        </div>
    )
}
