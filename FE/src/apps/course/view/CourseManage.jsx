import { useEffect, useState } from 'react'
import { Button } from 'primereact/button'
import { Toolbar } from 'primereact/toolbar'
import { DataTable } from 'primereact/datatable'
import { Column } from 'primereact/column'
import CourseForm from './CourseForm'
import { fetchCoursesForAdmin } from '@/apps/admin/api/course-api-rest'
import { DEFAULT_DATATABLE_CONFIG } from '@/common/constants/index'
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
        <>
            {/* ===== TOOLBAR ===== */}
            <Toolbar className='mb-2 p-0'
                left={() =>
                    <div class='align-items-center flex gap-2'>
                        <div class='font-bold ml-3'>
                            <h3>Course Management</h3>
                        </div>
                    </div>
                }
                right={() => (
                    <Button
                        label="Create"
                        icon="pi pi-plus"
                        onClick={openCreate}
                    />
                )}
            />

            {/* ===== TABLE ===== */}
            <DataTable
                value={courses}
                loading={loading}
                dataKey="id"
                rowHover
                stripedRows
                showGridlines={false}
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
                                text
                                onClick={() => {
                                    setSelectedId(row.id)
                                    setMode('detail')
                                    setShowForm(true)
                                }}
                            />
                            <Button
                                icon="pi pi-pencil"
                                text
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
        </>
    )
}
